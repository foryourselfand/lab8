package collection;

import application.ContextServer;
import elements.Product;
import elements.UnitOfMeasure;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ProductList {
	private final ContextServer context;
	private final ReentrantLock locker;
	private ArrayList<Product> products;
	
	public ProductList(ContextServer context) {
		this.context = context;
		products = new ArrayList<>();
		locker = new ReentrantLock();
	}
	
	public String getTable() {
		locker.lock();
		StringBuilder result = new StringBuilder();
		for (Product product : products) {
			result.append(product.getIdUser()).append(" ");
			result.append(product.getId()).append(" ");
			result.append(product.getName()).append(" ");
			result.append(product.getCoordinates().getX()).append(" ");
			result.append(product.getCoordinates().getY()).append(" ");
			result.append(product.getCreationDate()).append(" ");
			result.append(product.getPrice()).append(" ");
			result.append(product.getPartNumber()).append(" ");
			result.append(product.getManufactureCost()).append(" ");
			result.append(product.getUnitOfMeasure()).append(" ");
			result.append(product.getOrganization().getId()).append(" ");
			result.append(product.getOrganization().getName()).append(" ");
			result.append(product.getOrganization().getAnnualTurnover()).append(" ");
			result.append(product.getOrganization().getOrganizationType()).append(" ");
		}
		locker.unlock();
		return result.toString();
	}
	
	public String printInfo() {
		locker.lock();
		String result = "Тип коллекции: " + products.getClass() + ", Размер: " + products.size();
		locker.unlock();
		return result;
	}
	
	public void addFromDatabase(Product item) {
		locker.lock();
		products.add(item);
		locker.unlock();
	}
	
	public String add(Product item, int idUser) {
		locker.lock();
		item.generateCreationDate();
		item.setIdUser(idUser);
		try {
			item = context.getHandlerDatabase().addProduct(item);
			products.add(item);
			locker.unlock();
			return "Элемент добавлен.";
		} catch (SQLException e) {
			locker.unlock();
			return "Элемент не удалось добавить в базу данных.";
		}
	}
	
	public String addIfMin(Product item, int idUser) {
		locker.lock();
		double price = Collections.min(products, Comparator.comparingDouble(Product::getPrice)).getPrice();
		if (price > item.getPrice()) {
			add(item, idUser);
			locker.unlock();
			return "Элемент наименьший, поэтому он добавлен.";
		}
		locker.unlock();
		return "Элемент не является наименьшим, поэтому он добавлен не был.";
	}
	
	public String reverse() {
		locker.lock();
		Collections.reverse(products);
		locker.unlock();
		return "Коллекция выставлена в обратном порядке.";
	}
	
	public String clear(int idUser) {
		locker.lock();
		IntStream.range(0, products.size()).filter(i->products.get(i).getIdUser() == idUser).forEach(this::remove);
		locker.unlock();
		return "Ваши элементы удалены.";
	}
	
	public String removeById(int id, int idUser) {
		locker.lock();
		for (Product product : products) {
			if (product.getId() == id) {
				if (product.getIdUser() != idUser) {
					locker.unlock();
					return "Вы не можете удалить этот элемент Product, так как он вам не принадлежит.";
				} else {
					remove(product);
					locker.unlock();
					return "Элемент Product с таким id удален.";
				}
			}
		}
		locker.unlock();
		return "Элемент Product с таким id не найден.";
	}
	
	public void remove(int index) {
		locker.lock();
		try {
			context.getHandlerDatabase().removeProduct(products.get(index));
		} catch (SQLException e) {
			locker.unlock();
			return;
		}
		products.remove(index);
		locker.unlock();
	}
	
	public void remove(Product product) {
		locker.lock();
		try {
			context.getHandlerDatabase().removeProduct(product);
		} catch (SQLException e) {
			locker.unlock();
			return;
		}
		products.remove(product);
		locker.unlock();
	}
	
	public String removeFirst(int idUser) {
		locker.lock();
		try {
			if (products.get(0).getIdUser() == idUser) {
				remove(0);
				locker.unlock();
				return "Первый элемент коллекции удален.";
			} else {
				locker.unlock();
				return "Элемент удалить не удалось, т.к. он вам не принадлежит.";
			}
		} catch (IndexOutOfBoundsException e) {
			locker.unlock();
			return "Коллекция пуста, первого элемент удалить не удалось.";
		}
	}
	
	public String updateById(int id, Product item, int idUser) {
		locker.lock();
		for (Product product : products) {
			if (product.getId() == id) {
				if (product.getIdUser() != idUser) {
					locker.unlock();
					return "Product нельзя обновить, так как он вам не принадлежит.";
				} else {
					product.updateProduct(item);
					try {
						context.getHandlerDatabase().updateProduct(product);
						locker.unlock();
						return "Элемент Product обновлен.";
					} catch (SQLException e) {
						locker.unlock();
						return "Обновить элемент в базе данных не удалось.";
					}
				}
			}
		}
		locker.unlock();
		return "Элемент Product с таким id не найден.";
	}
	
	public String removeAllByManufactureCost(Long manufactureCost, int idUser) {
		locker.lock();
		IntStream.range(0, products.size()).filter(i->products.get(i).getManufactureCost().equals(manufactureCost)).filter(i->products.get(i).getIdUser() == idUser).forEach(this::remove);
		locker.unlock();
		return "Команда выполнена!";
	}
	
	public String printLessThanUnitOfMeasure(UnitOfMeasure unitOfMeasure) {
		locker.lock();
		if (unitOfMeasure == UnitOfMeasure.SQUARE_METERS || unitOfMeasure == UnitOfMeasure.MILLIGRAMS || unitOfMeasure == UnitOfMeasure.MILLILITERS) {
			locker.unlock();
			return "Искомых элементов нет.";
		}
		ArrayList<Product> result = (ArrayList<Product>) products.stream().filter((product->unitOfMeasure.ordinal() >
				product.getUnitOfMeasure().ordinal() && unitOfMeasure.ordinal() - product.getUnitOfMeasure().ordinal() == 1)).collect(Collectors.toList());
		locker.unlock();
		return getAlphabet(result);
	}
	
	public void sort() {
		locker.lock();
		products = (ArrayList<Product>) products.stream().sorted(Comparator.comparingDouble(Product::getPrice)).collect(Collectors.toList());
		locker.unlock();
	}
	
	public String getAlphabet() {
		return getAlphabet(new ArrayList<>(products));
	}
	
	public String getAlphabet(List<Product> srcProducts) {
		locker.lock();
		srcProducts = srcProducts.stream()
				.sorted(Comparator.comparing(Product::getName)).collect(Collectors.toList());
		locker.unlock();
		return srcProducts.toString();
	}
	
	public void sort(Comparator<Product> comparator) {
		locker.lock();
		products = (ArrayList<Product>) products.stream().sorted(comparator).collect(Collectors.toList());
		locker.unlock();
	}
	
	public String printAscending() {
		locker.lock();
		ArrayList<Product> result = (ArrayList<Product>) new ArrayList<>(products).stream()
				.sorted(Comparator.comparingDouble(Product::getPrice)).collect(Collectors.toList());
		locker.unlock();
		return result.toString();
	}
	
	public Product[] getArray() {
		locker.lock();
		Product[] result = new Product[products.size()];
		for (int i = 0; i < products.size(); i++)
			result[i] = products.get(i);
		locker.unlock();
		return result;
	}
}
