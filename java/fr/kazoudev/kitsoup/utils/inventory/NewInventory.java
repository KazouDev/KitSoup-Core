package fr.kazoudev.kitsoup.utils.inventory;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.blockentity.BlockEntityChest;
import cn.nukkit.inventory.ContainerInventory;
import cn.nukkit.inventory.DoubleChestInventory;
import cn.nukkit.inventory.InventoryHolder;
import cn.nukkit.inventory.InventoryType;
import cn.nukkit.item.Item;
import cn.nukkit.level.GlobalBlockPalette;
import cn.nukkit.math.BlockVector3;
import cn.nukkit.math.Vector3;
import cn.nukkit.network.protocol.ContainerOpenPacket;
import cn.nukkit.network.protocol.UpdateBlockPacket;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class NewInventory extends ContainerInventory {
    private String title;

    protected final Map<Player, List<BlockVector3>> blockPositions = new HashMap<>();

    public NewInventory(InventoryHolder holder, InventoryType type, String title) {
        super(holder, type);
        this.title = title;
    }

    @Override
    public void onOpen(Player who) {
        this.viewers.add(who);

        List<BlockVector3> blocks = onOpenBlock(who);
        blockPositions.put(who, blocks);

        onNewOpen(who, blocks);
    }

    protected void onNewOpen(Player who, List<BlockVector3> blocks) {
        BlockVector3 blockPosition = blocks.isEmpty() ? new BlockVector3(0, 0, 0) : blocks.get(0);

        ContainerOpenPacket containerOpen = new ContainerOpenPacket();
        containerOpen.windowId = who.getWindowId(this);
        containerOpen.type = this.getType().getNetworkType();
        containerOpen.x = blockPosition.x;
        containerOpen.y = blockPosition.y;
        containerOpen.z = blockPosition.z;

        who.dataPacket(containerOpen);

        this.sendContents(who);
    }

    protected abstract List<BlockVector3> onOpenBlock(Player who);

    @Override
    public void onClose(Player who) {
        this.viewers.remove(who);

        List<BlockVector3> blocks = blockPositions.get(who);

        for (int i = 0, size = blocks.size(); i < size; i++) {
            final int index = i;
            Server.getInstance().getScheduler().scheduleDelayedTask(() -> {
                Vector3 blockPosition = blocks.get(index).asVector3();
                UpdateBlockPacket updateBlock = new UpdateBlockPacket();
                updateBlock.blockRuntimeId = GlobalBlockPalette.getOrCreateRuntimeId(who.getLevel().getBlock(blockPosition).getFullId());
                updateBlock.flags = UpdateBlockPacket.FLAG_ALL_PRIORITY;
                updateBlock.x = blockPosition.getFloorX();
                updateBlock.y = blockPosition.getFloorY();
                updateBlock.z = blockPosition.getFloorZ();

                who.dataPacket(updateBlock);
            }, 2 + i, false);
        }
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getName(){
        return title;
    }
}
