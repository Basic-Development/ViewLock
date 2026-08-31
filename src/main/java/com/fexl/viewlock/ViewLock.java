package com.fexl.viewlock;

import com.fexl.viewlock.client.commands.ViewLockCommand;
import com.fexl.viewlock.event.ClientFrameRenderEvents;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.resources.Identifier;
import net.minecraft.world.InteractionResult;
import org.lwjgl.glfw.GLFW;

public class ViewLock implements ClientModInitializer {

    //Main keys
    public static KeyMapping axisAlignKey;
    public static KeyMapping pitchKey;
    public static KeyMapping yawKey;
    //ToDo: Clean this up so it looks better in the player Controls editor menu
    public KeyMapping.Category category = KeyMapping.Category.register(
            Identifier.fromNamespaceAndPath("viewlock", "main")
    );

    //Function for client-side command registering
    public static void registerClientCommands(CommandDispatcher<FabricClientCommandSource> dispatcher, CommandBuildContext registryAccess) {
        ViewLockCommand.register(dispatcher);
    }

    @Override
    public void onInitializeClient() {
        //Register client-side commands
        ClientCommandRegistrationCallback.EVENT.register(ViewLock::registerClientCommands);
        ClientFrameRenderEvents.FRAME_RENDER.register(() -> {
            LocalPlayer player = Minecraft.getInstance().player;
            if (player == null) {
                return InteractionResult.FAIL;
            }
            ViewModify.changeView(player);
            return InteractionResult.PASS;
        });

        //Defines the axis-align key (default "Y")
        axisAlignKey = KeyMappingHelper.registerKeyMapping(new KeyMapping(
                "key.viewlock.axisalign.name",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_Y,
                this.category

        ));

        //Defines the pitch lock key (default "U")
        pitchKey = KeyMappingHelper.registerKeyMapping(new KeyMapping(
                "key.viewlock.pitch.name",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_U,
                this.category

        ));

        //Defines the yaw lock key (default "I")
        yawKey = KeyMappingHelper.registerKeyMapping(new KeyMapping(
                "key.viewlock.yaw.name",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_I,
                this.category

        ));
    }

}



