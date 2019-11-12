
# README

Dieses Tool konvertiert eine csv-Datei in eine xml-Datei, wobei die XML-Datei in die WISO Steuersoftware eingelesen werden kann. 

<img src="https://wiki.vuplus-support.org/images/thumb/5/50/Achtung_gelb.png/685px-Achtung_gelb.png" width="100px" /> **Vorsicht Spagetti Code!!! Quick and Dirty Solution!!!**

## Voraussetzungen

* Java muss auf dem Rechner installiert sein.
* Die csv Datei muss in test.txt umbenannt werden.
* test.txt muss im selben Ordner wie die .jar-Datei liegen

## Ausführen Möglichkeit 1:

```
java -jar Main.jar Peter_Mustermann privat
```


### Erklärung:

Alle Fahrten sind privat und Fahrer ist Peter_Mustermann


### Beispiel CSV Zeile:

```
Fahrt;09.10.2017 23:27;09.10.2017 23:34;00:07;Arbeit;112796,6;Deutscher Platz;42;04103;Leipzig;Deutschland;Zielort;112800,6;Trendelenburgstraße;;04289;Leipzig;Deutschland;3,9;KFZ Peter;L-JZ 2;Skoda;Octavia;;;;;;;
```


## Ausführen Möglichkeit 2:

```
java -jar Main.jar Peter_Mustermann
```


### Erklärung:

Alle Fahrten sind von Peter_Mustermann gefahren
Es muss als erste Spalte in jeder Zeile die "fahrtart" eingetragen werden.


### Möglichkeiten für "fahrtart":

* Dienstreise
* Einsatzwechseltätigkeit
* Fahrtätigkeit
* Privatfahrt
* Privatfahrt mit Details
* Fahrt zur Arbeit
* Fahrt von der Arbeit
* Fahrt zum doppelten Haushalt
* Fahrt vom doppelten Haushalt


### Beispiel CSV Zeile:

```
Fahrt zur Arbeit;Fahrt;09.10.2017 23:27;09.10.2017 23:34;00:07;Arbeit;112796,6;Deutscher Platz;42;04103;Leipzig;Deutschland;Zielort;112800,6;Trendelenburgstraße;;04289;Leipzig;Deutschland;3,9;KFZ Peter;L-JZ 2;Skoda;Octavia;;;;;;;
```
