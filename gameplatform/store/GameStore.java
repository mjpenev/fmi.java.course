package bg.sofia.uni.fmi.mjt.gameplatform.store;

import bg.sofia.uni.fmi.mjt.gameplatform.store.item.StoreItem;
import bg.sofia.uni.fmi.mjt.gameplatform.store.item.filter.ItemFilter;

import java.math.BigDecimal;
import java.util.Arrays;

public class GameStore implements StoreAPI{

    private StoreItem[] availableItems;
    public GameStore(StoreItem[] availableItems) {
        this.availableItems = availableItems;
    }

    @Override
    public StoreItem[] findItemByFilters(ItemFilter[] itemFilters) {
        StoreItem[] result = new StoreItem[0];

        for (StoreItem item : this.availableItems) {
            boolean matchesAll = true;
            for (ItemFilter filter : itemFilters) {
                if (!filter.matches(item)) {
                    matchesAll = false;
                    break;
                }
            }
            if (matchesAll) {
                StoreItem[] newArray = new StoreItem[result.length + 1];
                System.arraycopy(result, 0, newArray, 0, result.length);
                newArray[result.length] = item;
                result = newArray;
            }
        }
        return result;
    }

    @Override
    public void applyDiscount(String promoCode) {
        for (StoreItem item : availableItems) {
            if (promoCode.matches("VAN40")) {
                item.setPrice(item.getPrice().multiply(BigDecimal.valueOf(0.6)));
            }
            else if (promoCode.matches("100YO")) {
                item.setPrice(BigDecimal.valueOf(0));
            }
            else {
                System.out.println("Not a valid promo code");
            }
        }
    }

    @Override
    public boolean rateItem(StoreItem item, int rating) {
        if (rating < 1 || rating > 5 || item == null) {
            return false;
        }
        item.rate(rating);
        return true;
    }
}
