# poe-inventory

This Scala program allows you to automate inventory management. Some features include:

 - Empty inventory
    - Intelligently (Supports Currency, Essence, Div Card, Quad, Normal, and Premium Tabs)
    - Blindly (useful for trading bulk currency)
 - Sort Maps by Tier
 - Chaos / Regal recipe

## Installation

**For 64-Bit Windows:**

Copy `system32\JIntellitype64.dll` to `C:\Windows\System32` and rename it to `JIntellitype.dll`. This enables the program to bind to the hotkeys

**For 32-Bit Windows:**

Copy `system32\JIntellitype.dll` to `C:\Windows\System32`. This enables the program to bind to the hotkeys

## Usage

Default bindings are defined in Main.scala and are as follows:


| Command               | Hotkey           | Comments                                                                        |
|-----------------------|------------------|---------------------------------------------------------------------------------|
| Quit                  | Ctrl + Shift + Q |                                                                                 |
| Smart Empty Inventory | Ctrl + Shift + B | Puts items in the correct tabs                                                  |
| Get Chaos Recipe      | Ctrl + Shift + F |                                                                                 |
| Get Regal Recipe      | Ctrl + Shift + G |                                                                                 |
| Dump Inventory        | Ctrl + Shift + V | Dumps items from your inventory to whatever is open (tab or trade window)       |
| Calibrate             | Ctrl + Shift + T | Creates a screenshot with the current understanding of your inventory locations |