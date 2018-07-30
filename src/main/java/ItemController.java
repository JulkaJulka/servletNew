public class ItemController {

    private ItemService itemService = new ItemService();

    public Item save(Item item) throws BadRequestException {
        return itemService.save(item);
    }

    private Item update(Item item) throws BadRequestException{
        return itemService.update(item);
    }
    private void delete(long id) throws BadRequestException{
        itemService.delete(id);
    }
}
