package top.ialdaiaxiariyay.rtt.mixin.gto;

import com.gto.gtocore.integration.emi.EMIManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Mixin({ EMIManager.class })
public class EMIManagerMixin {

    @Redirect(
              method = "<init>",
              at = @At(
                       value = "INVOKE",
                       target = "Ljava/util/List;addAll(Ljava/util/Collection;)Z",
                       ordinal = 0),
              remap = false)
    private boolean redirectAddAll(List instance, Collection c) {
        return instance.addAll(c != null ? c : Collections.emptyList());
    }
}
