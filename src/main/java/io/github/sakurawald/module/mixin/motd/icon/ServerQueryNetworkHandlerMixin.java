/*
 * This file is part of MiniMOTD, licensed under the MIT License.
 *
 * Copyright (c) 2020-2023 Jason Penilla
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package io.github.sakurawald.module.mixin.motd.icon;

import io.github.sakurawald.module.common.manager.Managers;
import io.github.sakurawald.module.initializer.motd.MotdInitializer;
import io.github.sakurawald.core.auxiliary.LogUtil;
import io.github.sakurawald.core.auxiliary.minecraft.ServerHelper;
import net.minecraft.server.network.ServerQueryNetworkHandler;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Optional;
import net.minecraft.server.ServerMetadata;

@Mixin(ServerQueryNetworkHandler.class)
public abstract class ServerQueryNetworkHandlerMixin {

    @Unique
    private static final MotdInitializer module = Managers.getModuleManager().getInitializer(MotdInitializer.class);

    @Redirect(method = "onRequest", at = @At(value = "FIELD", target = "Lnet/minecraft/server/network/ServerQueryNetworkHandler;metadata:Lnet/minecraft/server/ServerMetadata;"))
    public @NotNull ServerMetadata handleStatusRequest(final net.minecraft.server.network.ServerQueryNetworkHandler instance) {
        ServerMetadata vanillaStatus = ServerHelper.getDefaultServer().getServerMetadata();
        if (vanillaStatus == null) {
            LogUtil.warn("Can't inject into the vanilla server status. (reason: the vanilla one is null)");
            return new ServerMetadata(module.getRandomDescription(), Optional.empty(), Optional.empty(), module.getRandomIcon(), false);
        }

        return new ServerMetadata(module.getRandomDescription(), vanillaStatus.comp_1274(), vanillaStatus.comp_1275(), module.getRandomIcon(), vanillaStatus.secureChatEnforced());
    }
}
