package com.lilithsthrone.game.slavery;

import com.lilithsthrone.game.character.npc.NPC;

public class StressReliefRoomPartnerData {
    private NPC partner;
    private StressReliefRoomActionCategories action;

    public StressReliefRoomPartnerData(NPC partner, StressReliefRoomActionCategories action) {
        this.partner = partner;
        this.action = action;
    }

    public NPC getPartner() {
        return partner;
    }

    public StressReliefRoomActionCategories getAction() {
        return action;
    }
}
