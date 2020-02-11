package Models;

    import java.util.List;

    public class Cart {
        List<Item> itemList;

        public Cart(List<Item> itemList) {
            this.itemList = itemList;
        }

        public List<Item> getItemList() {
            return itemList;
        }

        public void setItemList(List<Item> itemList) {
            this.itemList = itemList;
        }
    }


