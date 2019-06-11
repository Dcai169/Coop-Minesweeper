Minesweeper
===

#### Controls:
* Shift/LMB: Reveal tile
* Space/RMB: Flag tile
* Q: Reveal board
* W: Regenerate board
* E: Hide board
* Esc: Quit

#### Settings:
Requires Java SE 6

Game Parameters are customizable in Settings.java

To play Co-op,
* `TARGET_ADDRESS` must be set to your partner's IP address.
* `INCOMING_ADDRESS` must be set to your IP address.
* There must be exactly one server and one client.
* Your `NAME` must not be the same as your partners.
* If you are playing with a large board, it may be necessary 
to increase the size of the byte array `buffer` in the `UDPListener` class

#### Known Bugs:
* Actions `reveal`, `hide`, and `newGame` are not reflected by the partner.
* `UDPListener.modified` seems to get stuck on `true`.
