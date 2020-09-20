/***************************************************************************
 *                      (C) Copyright 2020 - Stendhal                      *
 ***************************************************************************
 ***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/
package games.stendhal.server.maps.quests.revivalweeks;

import java.util.Arrays;
import java.util.Map;

import games.stendhal.common.Direction;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.core.engine.dbcommand.ReadGroupQuestCommand;
import games.stendhal.server.core.events.TurnListener;
import games.stendhal.server.core.events.TurnNotifier;
import games.stendhal.server.entity.npc.ConversationPhrases;
import games.stendhal.server.entity.npc.ConversationStates;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.condition.QuestCompletedCondition;
import games.stendhal.server.util.QuestUtils;
import marauroa.server.db.command.DBCommandQueue;

public class BuilderNPC implements LoadableContent, TurnListener {
	private SpeakerNPC npc = null;
	private static final String QUEST_SLOT = QuestUtils.evaluateQuestSlotName("minetown_construction_[year]");
	private ReadGroupQuestCommand command;
	private Map<String, Integer> progress;

	@Override
	public void addToWorld() {
		this.command = new ReadGroupQuestCommand(QUEST_SLOT);
		DBCommandQueue.get().enqueue(command);
		TurnNotifier.get().notifyInTurns(0, this);
	}
			
	@Override
	public void onTurnReached(int currentTurn) {
		if (command.getProgress() == null) {
			TurnNotifier.get().notifyInTurns(0, this);
			return;
		}
		this.progress = command.getProgress();
		addNPC();
	}

	private void addNPC() {
		final StendhalRPZone zone = SingletonRepository.getRPWorld().getZone("0_semos_mountain_n2");
		final SpeakerNPC npc = new SpeakerNPC("Klaus") {

			@Override
			protected void createPath() {
				// npc does not move
				setPath(null);
			}

			@Override
			protected void createDialog() {
				addGreeting("Hi, there.");
				addHelp("#Mine #Town #Revival #Weeks is an annual festival.");
				addQuest("We have run short of supplies and may not be able to finish contruction in time! What a desaster.");
				addJob("I am the construction manager responsible for setting up #Mine #Town #Revival #Weeks.");
				addGoodbye("Bye, come back soon.");

			}

		};

		npc.setOutfit("body=0,dress=55,head=2,mouth=0,eyes=18,mask=0,hair=25,hat=1");
		npc.setPosition(90, 111);
		npc.setDirection(Direction.DOWN);
		npc.initHP(100);
		npc.setDescription("You see Klaus. He is in charge of construction.");
		zone.add(npc);
		
		addQuestDialog(npc);
	}

	

	private void addQuestDialog(SpeakerNPC npc) {
		npc.add(ConversationStates.IDLE, 
				ConversationPhrases.GREETING_MESSAGES,
				new QuestCompletedCondition(QUEST_SLOT),
				ConversationStates.ATTENDING,
				"Thanks again for your help. The #status of the construction is improving. Hopefully we will be finished in time.",
				null);
		npc.addReply(Arrays.asList("Mine", "Town", "Revival", "Weeks", "Mine Town",
				"Mine Town Revival", "Mine Town Revival Weeks", "Mine Town", "Revival Weeks"),
				"During the Revival Weeks we #celebrate the old and now mostly dead Mine Town north of Semos City. It has been a tradition for many years, but this year the #status of the build up is not looking well.",
				null);

		npc.addReply(Arrays.asList("status", "progress"),
				"There is still so much to do before the #Mine #Town #Revival #Weeks can start.",
				null);
	}

	@Override
	public boolean removeFromWorld() {
		if (npc != null) {
			npc.getZone().remove(npc);
		}
		return true;
	}

}
