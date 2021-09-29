package JDBC.zad2.domain;

public class OrderItem extends Entity{
	
		private int orderId;
		private String item;
		private String description;
		private double price;
		
		public OrderItem() {
			
		}
		
		public OrderItem(int id, int orderId, String item, String description, double price) {
			
			super.setId(id);
			this.orderId = orderId;
			this.item = item;
			this.description = description;
			this.price = price;
		}


		
		
		public int getOrderId() {
			return orderId;
		}

		public void setOrderId(int orderId) {
			this.orderId = orderId;
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
