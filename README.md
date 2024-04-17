Also, mein Vorschlag wäre folgender:
- basic game Struktur habe ich implementiert
- natürlich ist das design des main Menüs noch nicht schön und das Bild ist auch nur exemplarisch. Dies w+rde ich aber später von den designs eurer Spiele abhängig machen.
- Für die Zusammenarbeit habe ich ein GitHub repository angelegt.


Jedes gameX steht für ein Minispiel, das heißt jeder ordnet sich einem zu. Das ist nicht der endgültige Name, gilt erstmal nur der Zuordnung im Programm. Ich Beispielsweise würde Game1 nehmen.
Jeder hat:
1 Package mit dem Namen (hier:) game1
darin eine Klasse: Game1Controller
zusätzlich eine game1-view.fxml
Ich habe jedem eine Grundstruktur aufgebaut. Das heißt view und controller sind verknüpft und es gibt einen button, der zum Hauptmenü zurückkehrt. Natürlich könnt ihr alles ändern, es wäre nur schön wenn es in jedem Spiel die Möglichkeit gäbe zurück zum Menü zu kommen.
Momentan funktioniert dies so. es gibt einen Button mit der ID "backToMenu" und die zugehörige onAction Methode "backToMenuClick". (siehe in eurem Controller und eurer.fxml) (edited) 


---

Ich würde euch bitten,:
1. einen GitHub Acc. erstellen falls noch nicht vorhanden, dann mir euren Username schicken.
2. Nachdem ich euch als Contributer hinzugefügt habe, bitte das Projekt in euer Intellij klonen.

---

Wenn das geschehen ist, würde ich vorschlagen wir setzen uns kurz zusammen. Mein Plan wäre dann nämlich:
- jeder arbeitet NUR an seinen Dateien, d.h. seine .fxml und in seinem Package für controller und evtl. Models
- jeder hat einen branch, der vom main branch erbt.
- der main branch besteht momentan aus der Grundstruktur von mir, ab dem Punkt wo jeder seinen Branch hat darf nur noch nach Absprache auf den main-Branch gepusht werden
- Wenn alle ihr Spiel fertig, lauffähig und clean haben, dann ersteltt jeder einen pull-request auf den main branch. Jeder andere ist dann code-reviewer, muss sich in den Code einlesen, ihn verstehen und evtl. kritisieren
 ---

Wichtig: falls jemand an dem technischem Aufbau (schönes Design kommt später) etwas stört, bitte frühzeitig bescheid sagen. Sobald ich mich tiefer mit meinem Spiel beschäftige, möchte ich die Grundstruktur des Gesamtspiels nicht mehr ändern.
