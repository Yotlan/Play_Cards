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

1. *Rule flip* : when a card A is put, and there is a card B of opponent next to A : Card A can be above/left/below/right to B. Card A must compare B with the position of card B relative to card A. When this compare proves that card A is superior, card B will be colored with the color of opponent. If card A is left to the card B, right value of card A must compare with left value of card B, otherwise the reverse. If card A is above card B, below value of card A must compare with above value of card B, otherwise the reverse. This compare 

   For example : Card A of player1 have 4 values : 1/2/3/4 (above/left/below/right) and Card B of player2 have 4 values : 2/3/4/5.  If card A is left to card B : 4>3 , card A is superior than card B. The color of card B will be colored as opponent color. 

2. *Rule Same* : turns over cards that have same values on 2 or more sides (with card of opponent). 

    For example : card A of player1 is 1/2/3/4 (above/left/below/right) , card B of player2 is above card A with values 2/3/1/5 and card C of player2 is on left side of card A with values 3/3/3/2. 
    - 1 (card A above) == 1 (card B below) 
    - 2 (card A left)== 2 (card C right)

  Thus rule will change the color of card B and card C to opposite color.  

3. *Rule Plus* : means that cards adding to the same value, on more than 2 adjacent sides are turned over.

    For example : card A of player1 is 1/2/3/4 (above/left/below/right) , card B of player2 is above card A with values 2/3/4/5 and card C of player2 is on left side of card A with values 3/3/3/3. 
    - 1+4 (card A above,card B below)
    - 2+3 (card A left ,card C right)
    - 1+4 == 2+3
  
  Thus rule will change the color of card B and card C to opposite color. 

4. *Rule Combo* : Cards turned over using Same or Plus turn over adjacent cards in Combo.

NB: In our version, their is only rule Flip and combo (with flip) is applied.


Reference :
- Triple Triade Rules FF8, https://game8.co/games/Final_Fantasy_VIII/archives/270175#hm_1