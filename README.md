# README #

Il gruppo si pone come obiettivo quello di realizzare una riproduzione in 2D del gioco da tavolo quoridor.

### Regole ###

Il gioco si compone di una tabella 9x9 dove due giocatori partono da due lati opposti. Lo scopo del gioco è raggiungere il lato di partenza dell’avversario, avvalendosi di 10 barriere per ostacolare i movimenti dell’altro giocatore.

Link alle regole:
http://quoridor.di.uoa.gr/rules.html

### Funzionalità minimali ritenute obbligatorie: ###
* Creazione menù di gioco;
* Possibilità di iniziare una partita (creazione della tabella di gioco);
* Possibilità dei giocatori di inserire il proprio nickname;
* Possibilità di salvare e riprendere la partita;
* Possibilità di visualizzare il ranking.

### Funzionalità opzionali: ###
* Creazione di un timer, per limitare il tempo totale che ogni giocatore ha a disposizione durante la partita;
* Possibilità di iniziare una partita personalizzata (numero di round, numero barriere, dimensione tabella, scadenza timer);
* Inserimento musica di sottofondo ed effetti sonori.
“Challenge” principali:
* Creazione di una GUI con JavaFX;
* Uso del pattern MVC;
* Gestione coerente degli elementi all’interno del gioco;
* Controllare che la partita non vada in stallo, cioè che ogni giocatore possa raggiungere il lato opposto.

### Suddivisione del lavoro: ###
* Becci Alessandro: GUI del menù iniziale, gestione del salvataggio e della LeaderBoard;
* D’Ambrosio Stefano: GUI del campo, power up;
* Castellani Thomas: logica del gioco;
* Fabri Luca: realizzazione dei modelli di gioco.

TODO list:

