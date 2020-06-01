package fr.kazoudev.kitsoup.utils.inventory;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.block.BlockID;
import cn.nukkit.blockentity.BlockEntity;
import cn.nukkit.inventory.InventoryType;
import cn.nukkit.level.GlobalBlockPalette;
import cn.nukkit.math.BlockVector3;
import cn.nukkit.nbt.NBTIO;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.network.protocol.BlockEntityDataPacket;
import cn.nukkit.network.protocol.UpdateBlockPacket;
import fr.kazoudev.kitsoup.utils.inventory.NewInventory;

import java.io.IOException;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.List;

public class NewChestInventory extends NewInventory {

    public NewChestInventory(InventoryType type, String title) {
        super(null, type, title);
    }

    @Override
    public void onOpen(Player who) {
        this.viewers.add(who);

        List<BlockVector3> blocks = onOpenBlock(who);
        blockPositions.put(who, blocks);

        Server.getInstance().getScheduler().scheduleDelayedTask(() -> {
            onNewOpen(who, blocks);
        }, 3);
    }

    @Override
    protected List<BlockVector3> onOpenBlock(Player who) {
        BlockVector3 blockPosition = new BlockVector3((int) who.x, ((int) who.y) + 2, (int) who.z);
        BlockVector3 blockPosition2 = blockPosition.add(1, 0, 0);

        placeChest(who, blockPosition);
        placeChest(who, blockPosition2);

        pair(who, blockPosition, blockPosition2);
        pair(who, blockPosition2, blockPosition);

        return Arrays.asList(blockPosition, blockPosition2);
        
    }

    private void pair(Player who, BlockVector3 pos1, BlockVector3 pos2) {
        BlockEntityDataPacket blockEntityData = new BlockEntityDataPacket();
        blockEntityData.x = pos1.x;
        blockEntityData.y = pos1.y;
        blockEntityData.z = pos1.z;
        blockEntityData.namedTag = getDoubleNbt(pos1, pos2, getName());

        who.dataPacket(blockEntityData);
    }

    protected void placeChest(Player who, BlockVector3 pos) {
        UpdateBlockPacket updateBlock = new UpdateBlockPacket();
        updateBlock.blockRuntimeId = GlobalBlockPalette.getOrCreateRuntimeId(BlockID.CHEST, 0);
        updateBlock.flags = UpdateBlockPacket.FLAG_ALL_PRIORITY;
        updateBlock.x = pos.x;
        updateBlock.y = pos.y;
        updateBlock.z = pos.z;

        who.dataPacket(updateBlock);

        BlockEntityDataPacket blockEntityData = new BlockEntityDataPacket();
        blockEntityData.x = pos.x;
        blockEntityData.y = pos.y;
        blockEntityData.z = pos.z;
        blockEntityData.namedTag = getNbt(pos, getName());

        who.dataPacket(blockEntityData);
    }

    private byte[] getNbt(BlockVector3 pos, String name) {
        CompoundTag tag = new CompoundTag()
                .putString("id", BlockEntity.CHEST)
                .putInt("x", pos.x)
                .putInt("y", pos.y)
                .putInt("z", pos.z)
                .putString("CustomName", name == null ? "Chest" : name);

        try {
            return NBTIO.write(tag, ByteOrder.LITTLE_ENDIAN, true);
        } catch (IOException e) {
            throw new RuntimeException("Unable to create NBT for chest");
        }
    }

    private static byte[] getDoubleNbt(BlockVector3 pos, BlockVector3 pairPos, String name) {
        CompoundTag tag = new CompoundTag()
                .putString("id", BlockEntity.CHEST)
                .putInt("x", pos.x)
                .putInt("y", pos.y)
                .putInt("z", pos.z)
                .putInt("pairx", pairPos.x)
                .putInt("pairz", pairPos.z)
                .putString("CustomName", name == null ? "Chest" : name);

        try {
            return NBTIO.write(tag, ByteOrder.LITTLE_ENDIAN, true);
        } catch (IOException e) {
            throw new RuntimeException("Unable to create NBT for chest");
        }
    }
}
