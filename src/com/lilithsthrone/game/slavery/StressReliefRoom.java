package com.lilithsthrone.game.slavery;

import com.lilithsthrone.game.character.CharacterUtils;
import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.main.Main;
import com.lilithsthrone.utils.Vector2i;
import com.lilithsthrone.utils.XMLSaving;
import com.lilithsthrone.world.Cell;
import com.lilithsthrone.world.WorldType;
import com.lilithsthrone.world.places.PlaceUpgrade;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

public class StressReliefRoom implements XMLSaving {

        private WorldType worldType;
        private Vector2i location;

        public StressReliefRoom(WorldType worldType, Vector2i location) {

            this.worldType = worldType;
            this.location = new Vector2i(location.getX(), location.getY());
        }

        public Element saveAsXML(Element parentElement, Document doc) {
            Element element = doc.createElement("stressReliefRoom");
            parentElement.appendChild(element);

            CharacterUtils.addAttribute(doc, element, "worldType", this.getWorldType().toString());
            CharacterUtils.addAttribute(doc, element, "x", String.valueOf(this.getLocation().getX()));
            CharacterUtils.addAttribute(doc, element, "y", String.valueOf(this.getLocation().getY()));

            return element;
        }

        public static StressReliefRoom loadFromXML(Element parentElement, Document doc) {
            try {

                StressReliefRoom room = new StressReliefRoom(
                        WorldType.valueOf(parentElement.getAttribute("worldType")),
                        new Vector2i(
                                Integer.valueOf(parentElement.getAttribute("x")),
                                Integer.valueOf(parentElement.getAttribute("y"))));



                return room;

            } catch(Exception ex) {
                System.err.println("Warning: StressReliefRoom failed to import!");
                return null;
            }
        }

        public static Cell getStressReliefCell(GameCharacter character) {
            List<Cell> freeStressReliefCells = new ArrayList<>();

            for(StressReliefRoom room : Main.game.getSlaveryUtil().getStressReliefRooms()) {
                Cell c = Main.game.getWorlds().get(room.getWorldType()).getCell(room.getLocation());

                int charactersPresent = Main.game.getCharactersPresent(c).size();

                if(character.getSlaveJobSettings().contains(SlaveJobSetting.STRESS_RELIEF_INDUSTRIAL) && c.getPlace().getPlaceUpgrades().contains(PlaceUpgrade.LILAYA_STRESS_RELIEF_ROOM_INDUSTRIAL) && charactersPresent<8) {
                    return c;
                } else if(character.getSlaveJobSettings().contains(SlaveJobSetting.STRESS_RELIEF_LUXURIOUS) && c.getPlace().getPlaceUpgrades().contains(PlaceUpgrade.LILAYA_STRESS_RELIEF_ROOM_LUXURIOUS) && charactersPresent<8) {
                    return c;
                } else if(character.getSlaveJobSettings().contains(SlaveJobSetting.STRESS_RELIEF_REGULAR) && !c.getPlace().getPlaceUpgrades().contains(PlaceUpgrade.LILAYA_STRESS_RELIEF_ROOM_LUXURIOUS)
                        && !c.getPlace().getPlaceUpgrades().contains(PlaceUpgrade.LILAYA_STRESS_RELIEF_ROOM_INDUSTRIAL) && charactersPresent<8) {
                    return c;
                }

                if(charactersPresent<8) {
                    freeStressReliefCells.add(c);
                }
            }
            if(freeStressReliefCells.isEmpty()) {
                return null;
            }
            return freeStressReliefCells.get(0);
        }



        public WorldType getWorldType() {
            return worldType;
        }

        public Vector2i getLocation() {
            return location;
        }
        }
