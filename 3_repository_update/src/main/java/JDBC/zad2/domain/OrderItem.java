package JDBC.zad2.domain;

public class OrderItem {

		private int id;
		private String item;
		private String description;
		private double price;
		
		public OrderItem() {
			
		}
		
		public OrderItem(int id, String item, String description, double price) {
			
			this.id = id;
			this.item = item;
			this.description = description;
			this.price = price;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getItem() {
			return item;
		}

		public void setItem(String name) {
			this.item = name;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public double getPrice() {
			return price;
		}

		public void setPrice(double price) {
			this.price = price;
		}
		
		
		
		
}
