package io.github.spinoscythe.orefindermod.item;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;

import java.util.Objects;

public class OreFinderItem extends Item {
    public OreFinderItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        if (pContext.getLevel().isClientSide) {
            var player = pContext.getPlayer();
            var clickedPos = pContext.getClickedPos();

            for (int i = 0; i <= clickedPos.getY() + 64; i++) {
                var blockBelow = pContext.getLevel().getBlockState(clickedPos.below(i));
                if (blockBelow.is(Tags.Blocks.ORES)) {
                    outputCoordinates(clickedPos.below(i), Objects.requireNonNull(player), blockBelow.getBlock());
                    pContext.getLevel().playSound(player, clickedPos, SoundEvents.NOTE_BLOCK_BELL.value(), SoundSource.BLOCKS);
                }
            }
        }
        pContext.getItemInHand().hurtAndBreak(1, Objects.requireNonNull(pContext.getPlayer()), LivingEntity.getSlotForHand(pContext.getHand()));
        return super.useOn(pContext);
    }

    private void outputCoordinates(BlockPos blockPos, Player player, Block blockBelow) {
        player.sendSystemMessage(Component.literal("Found " + Component.translatable(blockBelow.getDescriptionId()) + " at " + formatPos(blockPos)));
    }

    private String formatPos(BlockPos pos) {
        return "[" + pos.getX() + ", " + pos.getY() + "]";
    }
}
