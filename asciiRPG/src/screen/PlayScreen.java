/*
 * Copyright (C) 2015 Aeranythe Echosong
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package screen;

import world.*;
import asciiPanel.AsciiPanel;
import java.awt.event.KeyEvent;

/**
 *
 * @author Aeranythe Echosong
 */
public class PlayScreen implements Screen {

    private World world;
    private int centerX;
    private int centerY;
    private int screenWidth;
    private int screenHeight;

    public PlayScreen() {
        screenWidth = 80;
        screenHeight = 21;
        createWorld();
    }

    private void createWorld() {
        world = new WorldBuilder(90, 31)
                .makeCaves()
                .build();
    }

    private void scrollBy(int mx, int my) {
        centerX = Math.max(0, Math.min(centerX + mx, world.width() - 1));
        centerY = Math.max(0, Math.min(centerY + my, world.height() - 1));
    }

    private void displayTiles(AsciiPanel terminal, int left, int top) {
        for (int x = 0; x < screenWidth; x++) {
            for (int y = 0; y < screenHeight; y++) {
                int wx = x + left;
                int wy = y + top;

                terminal.write(world.glyph(wx, wy), x, y, world.color(wx, wy));
            }
        }
    }

    @Override
    public void displayOutput(AsciiPanel terminal) {
        displayTiles(terminal, getScrollX(), getScrollY());
        terminal.write('X', centerX - getScrollX(), centerY - getScrollY());
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        switch (key.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                scrollBy(-1, 0);
                break;
            case KeyEvent.VK_RIGHT:
                scrollBy(1, 0);
                break;
            case KeyEvent.VK_UP:
                scrollBy(0, -1);
                break;
            case KeyEvent.VK_DOWN:
                scrollBy(0, 1);
                break;
        }
        return this;
    }

    public int getScrollX() {
        return Math.max(0, Math.min(centerX - screenWidth / 2, world.width() - screenWidth));
    }

    public int getScrollY() {
        return Math.max(0, Math.min(centerY - screenHeight / 2, world.height() - screenHeight));
    }

}
