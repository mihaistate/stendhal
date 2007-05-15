package games.stendhal.server.maps.fado.weaponshop;

import games.stendhal.server.StendhalRPZone;
import games.stendhal.server.entity.npc.NPCList;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.maps.ZoneConfigurator;
import games.stendhal.server.entity.npc.SellerBehaviour;
import games.stendhal.server.entity.npc.ShopList;
import games.stendhal.server.pathfinder.Path;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Builds an NPC to buy previously un bought weapons.
 *
 * @author kymara
 */
public class RingSmithNPC implements ZoneConfigurator {

	private NPCList npcs = NPCList.get();

	private ShopList shops = ShopList.get();

	/**
	 * Configure a zone.
	 *
	 * @param	zone		The zone to be configured.
	 * @param	attributes	Configuration attributes.
	 */
	public void configureZone(StendhalRPZone zone, Map<String, String> attributes) {
		buildNPC(zone);
	}

	private void buildNPC(StendhalRPZone zone) {
		SpeakerNPC npc = new SpeakerNPC("Ognir") {

			@Override
			protected void createPath() {
				List<Path.Node> nodes = new LinkedList<Path.Node>();
				nodes.add(new Path.Node(17, 8));
				nodes.add(new Path.Node(15, 8));
				nodes.add(new Path.Node(15, 11));
				nodes.add(new Path.Node(17, 11));
				nodes.add(new Path.Node(17, 15));
				nodes.add(new Path.Node(18, 15));
				nodes.add(new Path.Node(18, 8));
				setPath(nodes, false);
			}

			@Override
			protected void createDialog() {
				addJob("I work with #gold, to fix and make jewellery.");
				addReply("request","Just ask about the #task if you want me to make a wedding ring for someone.");
				addReply("gold","It's cast from gold nuggets which you can pan for on Or'ril river. I don't cast it myself, but a smith in Ados does.");
				addHelp("You can sell weapons to Yorphin Baos over there. I sell jewelled rings and I can also make a wedding ring as a special #request.");
				addSeller(new SellerBehaviour(shops.get("sellrings")));
				addGoodbye("Bye, my friend.");
			}
		};
		npc.setDescription("You see Ognir, a friendly bearded chap.");
		npcs.add(npc);
		zone.assignRPObjectID(npc);
		npc.put("class", "ringsmithnpc");
		npc.set(17, 8);
		npc.initHP(100);
		zone.add(npc);

	}
}
