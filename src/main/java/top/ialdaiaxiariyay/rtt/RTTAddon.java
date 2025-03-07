package top.ialdaiaxiariyay.rtt;

import top.ialdaiaxiariyay.rtt.api.registries.RTTRegistration;
import top.ialdaiaxiariyay.rtt.common.data.ore.RTTOres;
import top.ialdaiaxiariyay.rtt.common.materials.RTTElements;

import com.gregtechceu.gtceu.api.addon.IGTAddon;
import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate;

public class RTTAddon implements IGTAddon {

    public void GTAddon() {}

    @Override
    public String addonModId() {
        return RTT.MOD_ID;
    }

    @Override
    public GTRegistrate getRegistrate() {
        return RTTRegistration.REGISTRATE;
    }

    @Override
    public void initializeAddon() {}

    @Override
    public void registerSounds() {}

    @Override
    public void registerCovers() {}

    @Override
    public boolean requiresHighTier() {
        return true;
    }

    @Override
    public void registerElements() {
        RTTElements.init();
    }

    @Override
    public void registerTagPrefixes() {}

    @Override
    public void registerOreVeins() {
        RTTOres.init();
    }
}
