# Java Library For Games
## About The Project
This project is a 2D tile-based engine built entirely in Java, designed from scratch during my second semester object oriented programming class. 

**Core features:** include tile based environment management, a state system, and A* heuristic pathfinding algorithm.

Whith the declared fuctions allows developers to create and edit maps directly through code, defining custom tiles with their own textures and collision properties.
Thanks to CellManager the program handles all tile loading, map drawing, and structure generation with simple methods like generateMap() and fill(), making it easy to prototype or build new environments quickly.

For navigation, the project implements a pathfinding system inspired by the A* algorithm. It uses a grid of nodes, Manhattan distance as a heuristic, and cost based evaluation to determine optimal routes, using my own implementation.
The algorithm reconstructs the path through parent nodes, allowing entities to move intelligently across the map while avoiding solid tiles.

The project includes two autonomous entities a Human and Pig each with their own behavioral logic and interaction with the environment:

* The Human entity actively explores the map selecting random allow points in a specific radius and take rests when he arrive to the point. 
Also the humans has hungerbars which decrease by walking, if the hungerbar is low, they start searching for Pigs using the pathfinding system to plan its movement and eat the pig.

* The Pig entity behaves passively, wandering within defined limits.
This interaction between entities and the pathfinding layer showcases a basic form of AI state behavior, where each agent makes independent decisions based on map data and other entities positions.

Overall, the project demonstrates a functional 2D simulation environment combining object-oriented design, AI behavior, and heuristic algorithms. This work showcases the full application of the concepts and methodologies acquired in my OOP class.

### Built With
* ![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
* ![Swing/AWT](https://img.shields.io/badge/Swing%2FAWT-5382A1?style=for-the-badge&logo=oracle&logoColor=white)

## Project working
![Grabación de pantalla 2025-10-07 a la(s) 4 18 12 p m](https://github.com/user-attachments/assets/0d2a2d07-5c7c-400b-b933-e2eb81a54a32)

## Human hunting and eating a pig example:
![Grabación de pantalla 2025-10-07 a la(s) 4 18 12 p m -2](https://github.com/user-attachments/assets/cc370255-9c27-43af-bfc0-cb71c2f19678)

## How to Run the Project

1. **Clone the repository**
   ```bash
   git clone https://github.com/your-username/Library-for-Java-games-Project.git
2. Find the file Main in the main directory and run the code
