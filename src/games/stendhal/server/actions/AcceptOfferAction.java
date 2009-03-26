package games.stendhal.server.actions;

import marauroa.common.game.RPAction;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.entity.item.Item;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.trade.Offer;
import games.stendhal.server.trade.Shop;

import static games.stendhal.common.Constants.*;

public class AcceptOfferAction implements ActionListener {
	
	public static void register() {
		CommandCenter.register(ACCEPT_OFFER_TYPE, new AcceptOfferAction());
	}


	public void onAction(final Player player, final RPAction action) {
		// TODO Auto-generated method stub
		// accept offer at trading center
		final int price = action.getInt(ACCEPT_OFFER_PRICE);
		final String itemName = action.get(ACCEPT_OFFER_ITEM);
		final String offererName = action.get(ACCEPT_OFFER_OFFERERNAME);
		final Shop shop = getShop();
		final Offer offer = createOffer(price, itemName, offererName);
		shop.acceptOffer(offer, player);
	}

	private Offer createOffer(final int price, final String itemName, final String offererName) {
		final Item item = SingletonRepository.getEntityManager().getItem(itemName);
		final Offer offer = new Offer(item, price, offererName);
		return offer;
	}

	private Shop getShop() {
		final Shop shop = new Shop();
		return shop;
	}

}
