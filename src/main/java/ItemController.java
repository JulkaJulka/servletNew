public class ItemController {

    private ItemService itemService = new ItemService();

    private Item save(Item item)  {
        return itemService.save(item);
    }

    private Item update(Item item){
        return itemService.update(item);
    }
    private void delete(Item item){
        itemService.delete(item);
    }
}
