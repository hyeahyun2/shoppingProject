package market.dao;

import java.util.ArrayList;

import market.dto.ProductDto;

public class ProductRepository {
	private ArrayList<ProductDto> listOfProducts = new ArrayList<ProductDto>();
	// ½Ì±ÛÅæ °´Ã¼
	private static ProductRepository instance = new ProductRepository();
	
	public static ProductRepository getInstance() { // ½Ì±ÛÅæ °´Ã¼ ¾ò±â
		return instance;
	}
	
	public ProductRepository() {
		ProductDto phone = new ProductDto("P1234", "iPhone 6s", 800000);
		phone.setDescription("4.7-inch, 1334x750 Renina HD display, 8-megapixel iSight Camera");
		phone.setCategory("Smart Phone");
		phone.setManufacturer("Apple");
		phone.setUnitsInStock(1000);
		phone.setCondition("New");
		phone.setFilename("P1234.png");

		ProductDto notebook = new ProductDto("P1235", "LG PC ±×·¥", 1500000);
		notebook.setDescription("13.3-inch, IPS LED display, 5rd Generation Intel Core processors");
		notebook.setCategory("Notebook");
		notebook.setManufacturer("LG");
		notebook.setUnitsInStock(1000);
		notebook.setCondition("Refurbished");
		notebook.setFilename("P1235.png");
		
		ProductDto tablet = new ProductDto("P1236", "Galaxy Tab s", 900000);
		tablet.setDescription("212.8*125.6*6 mm, Super AMOLED display, Octa-Core processor");
		tablet.setCategory("Tablet");
		tablet.setManufacturer("Samsung");
		tablet.setUnitsInStock(1000);
		tablet.setCondition("old");
		tablet.setFilename("P1236.png");
		
		listOfProducts.add(phone);
		listOfProducts.add(notebook);
		listOfProducts.add(tablet);
	}
	
	// ¸ðµç product Á¤º¸ ¾ò±â
	public ArrayList<ProductDto> getAllProducts(){
		return listOfProducts;    
	}
	
	// ¸Å°³º¯¼ö ID°ª¿¡ ÇØ´çµÇ´Â product Á¤º¸ ¾ò±â
	public ProductDto getProductById(String productId) {
		ProductDto productById = null;
		
		for(int i=0; i<listOfProducts.size(); i++) {
			ProductDto product = listOfProducts.get(i);
			if(product != null && product.getProductId() != null 
					&& product.getProductId().equals(productId)) {
				productById = product;
				break;
			}
		}
		return productById;
	}
	
	// ½Ì±ÛÅæ °´Ã¼¿¡ product °´Ã¼ Ãß°¡ (»óÇ° Ãß°¡ ±â´É)
	public void addProduct(ProductDto product) {
		listOfProducts.add(product);
	}
}
