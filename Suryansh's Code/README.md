# CLImon — Person A

The model folder holds the two classes used during a fight. A Pokemon has its own HP and three moves. A Move tracks how many uses remain (PP). Calling heal() restores HP and all three PP counts.

The data folder holds the roster, the file reader and the name generator. From this directory, compile Person A's classes with `javac --release 11 -d out model/*.java data/*.java`. The game entry point belongs to Person C.

To play the integrated terminal game, compile these classes with the team’s battle engine and game entry point.

## Adding a monster

Add one line to data/monsters.txt using the following order.

```text
name,type,hp,attack,defense,speed,move1,move2,move3
```

Each move uses `name:type:power:accuracy:pp`. For example, `Wing Jab:Normal:45:100:14` deals damage and starts with 14 uses. For a status move use zero power and append poison, sleep, paralysis or weaken as a sixth field. Choose Fire, Water, Grass or Normal for each type. Use a unique monster name. The loader ignores blank lines and comments starting with # and reports invalid rows by line number.

NameMonster.create("Suryansh") uses the first letter to choose a type and the sum of letter values to set stats. Copy the generated stats and moves into a roster line to include the monster in the menu. Suryansh is a Grass monster with the highest attack of the three. Redham is a Water monster with a Water Burst attack. Kartik is the fastest teammate monster.

## Balance check

The existing ProjectChecks class ran 100 automated fights per distinct pair with seed 2468. Each monster fought 800 times.

| Monster | Wins | Losses |
|---|---:|---:|
| Cinderjay | 429 | 371 |
| Reeflynx | 348 | 452 |
| Mossback | 547 | 253 |
| Dusthare | 381 | 419 |
| Flareyak | 356 | 444 |
| Brookowl | 346 | 454 |
| Suryansh | 412 | 388 |
| Redham | 352 | 448 |
| Kartik | 429 | 371 |
