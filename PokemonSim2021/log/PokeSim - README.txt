# pokemon-sim-2021

Goals: 
- create an MVC version of the 2020 PokemonSim



Game Specifications:
- player should have a team of pokemon
- player can check/modify their pokemon's status by...
	- reordering team
	- healing pokemon w/ items
	- viewing a single pokemon
		- viewing stats
		- viewing moves
		
- player should have a bag of items
- player can check/modify their bag
	- view items w/ associated quantities
	? select an item
		- use consumable items (heals, TMs)
		- use persistent items (map)
		- view item description
		
- player can travel to new areas and interact by...
	- battling wild pokemon
	- battling trainers
	- pokecenter
		- healing
		- storing / retrieving / checking from box
	- buying items at pokemart
	- story events
	
- pokemon battles
	- routine
		- player and opponent take turns
	- display 
		- both pokemon's names w/ health and level
	- choices
		- attacks / moves
		- run away
		- bag
			- throw pokeball/catch
			- heal pokemon
		- pokemon
			- choose another pokemon
				- switch
				- view moves
	- results
		- player wins 
			- gain exp
			- level up if needed
		- player loses
			- switch pokemon / flee
			- blackout
			
- when a pokemon levels up
	- its stats should increase
	- it should potentially learn a new move
	- it should potentially evolve

- pokemon should have
	- a species (e.g. Charmander)
	- a nickname (possibly null)
	- stats
		- health
		- attack
		- defense
		? sp atk
		? sp def
		? speed
	- other stats...
		- current health
		- current exp
		- total exp
		- level
		- exp until next level up
	- a list of known moves
		- each move has associated max PP, current PP
	- in battle...
		- a list of status effects
		- a list of buffs/debuffs

- each species should have
	- one (or two) types
	- base stats
	- a move list
	- zero or one evolutions


			
	

	