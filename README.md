# PLAY CARDS PROJECT

## GROUP MEMBERS

- Yotlan LE CROM (<https://github.com/Yotlan>)
- Endy YU (<https://github.com/endyappel>)
- Florian MONSION (<https://github.com/JayCramboui>)

## PRESENTATION

In this project, you can connect to the main server and you can decide to create a Cards Game Room to play with some people to a Cards Game you have chooseen. 

### CARDS GAMES AVAILABLE

Card games who're available is presented bellow :
- Triple Triade FF8
- Triple Triade FF14 (TODO)
- Koi Koi Wars (TODO)
- *More to come*

### How to work Play cards game ? 
1. At first, the server of the play cards must be started: start `StartServer` in path `/PlayCards/src/main/java/fr.playcards/`.
2. Seconde, you must start the UI of first player : run `PlayCardsApplication` , found in same path as first step. 
3. And for seconde player, you can run : `PlayCardsApplicationLocal`, found in same path as first step.

### How to join Play card game Room ?
1. User can entrer at first you pseudo name. 
2. User can see a choice-box that can choose a game between Triple Triade FF8, Triple Triade FF14 (TODO) and Koi Koi Wars (TODO).
3. When User click on create room, a game room will be created with the game that user has chosen in step 2.
4. User can join the play card game room with the botton Join. 

### Triple Triade FF8
A card game, that need 2 players. Each player have 5 cards. There is a game-board 3x3 (shared object between 2 players). each card has 4 values, whose values are positioned ***??across??*** them.It's a game round by round, each round a player can put a card to game-board. After each round, there are some rules will be applied. 
Rules will be applied : 

1. Rule flip : when a card A is put, and there is a card B next to A. This next can B is above/left/below/right to A. So A must compare the value with B. For example : A