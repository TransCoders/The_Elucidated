package gr.edu.serres.TrancCoder_TheElucitated.Controllers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import gr.edu.serres.TrancCoder_TheElucitated.Objects.Quest;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * Created by GamerX on 21/12/2016.
 */
public class QuestControllerTest {
    QuestController questController;
    @Before
    public void setUp() throws Exception {
        questController = new QuestController();
    }

    @After
    public void tearDown() throws Exception {
        questController = null;
    }

    @Test
    public void addQuest() throws Exception {
        String dialogue = "dialogue";
        Quest quest = new Quest().setDialogue(dialogue);
        questController.addQuest(quest.getDialogue(),quest);
        assertTrue(questController.getQuestHashMap().containsKey(dialogue));
        assertTrue(questController.getQuestHashMap().containsValue(quest));
    }

    @Test
    public void getQuestHashMap() throws Exception {
        assertNotNull(questController.getQuestHashMap());
    }

    @Test
    public void getQuestId() throws Exception {

    }

    @Test
    public void isNotUnlocked() throws Exception {
        String dialogue = "dialogue";
        Quest quest = new Quest().setDialogue(dialogue);
        questController.addQuest(quest.getDialogue(),quest);
        assertFalse(quest.isUnlocked());
    }

    @Test
    public void unlockQuest() throws Exception {
        String dialogue = "dialogue";
        Quest quest = new Quest().setDialogue(dialogue);
        questController.addQuest(quest.getDialogue(),quest);
        questController.unlockQuest(quest.getDialogue());
        assertTrue(questController.getQuestHashMap().get(quest.getDialogue()).isUnlocked());
    }

}