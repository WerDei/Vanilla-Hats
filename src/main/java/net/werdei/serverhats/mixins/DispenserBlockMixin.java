package net.werdei.serverhats.mixins;

import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.werdei.serverhats.Config;
import net.werdei.serverhats.ServerHats;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(DispenserBlock.class)
public class DispenserBlockMixin
{
    private final DispenserBehavior hatDispenserBehavior = new ItemDispenserBehavior()
    {
        protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack)
        {
            return dispenseHat(pointer, stack) ? stack : super.dispenseSilently(pointer, stack);
        }
    };

    private static boolean dispenseHat(BlockPointer pointer, ItemStack armor)
    {
        BlockPos blockPos = pointer.getPos().offset(pointer.getBlockState().get(DispenserBlock.FACING));
        List<LivingEntity> list = pointer.getWorld().getEntitiesByClass(
                LivingEntity.class,
                new Box(blockPos),
                EntityPredicates.EXCEPT_SPECTATOR.and(new EntityPredicates.Equipable(new ItemStack(Items.CREEPER_HEAD))));
                // Use a fake item that can be equipped to a head slot. Can't use the hat item itself because
                // entities might do a getPreferredEquipmentSlot() call to test is that slot is empty. We'd have to
                // track and

        if (list.isEmpty()) return false;

        LivingEntity livingEntity = list.get(0);
        ItemStack itemStack = armor.split(1);
        livingEntity.equipStack(EquipmentSlot.HEAD, itemStack);

        if (livingEntity instanceof MobEntity)
        {
            ((MobEntity)livingEntity).setEquipmentDropChance(EquipmentSlot.HEAD, 2.0F);
            ((MobEntity)livingEntity).setPersistent();
        }

        return true;
    }

    
    @Inject(method = "getBehaviorForItem", at = @At("RETURN"), cancellable = true)
    protected void allowDispenserEquipping(ItemStack stack, CallbackInfoReturnable<DispenserBehavior> cir)
    {
        if (Config.dispenserEquipping && ServerHats.isItemAllowed(stack)
                && cir.getReturnValue() instanceof ItemDispenserBehavior) // Is a default behaviour
            cir.setReturnValue(hatDispenserBehavior);
    }
}
