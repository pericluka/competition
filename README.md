# competition

Here is a Clojure CRUD web application built for a faculty project. The app allows all users to see a list of football teams and a list of football players in the particular team. In order to create a new football team or to create a player for a particular team, you need to be logged in to the app as admin (username: admin, password: admin). Players can only be created for existing teams. You can choose the player position from a drop-down list.  Admin can also edit or delete existing players and teams.

## Installation

In order to create a database, you will need to run a SQL script: competition.sql and after that, you will need to change the user and password in the databaseConfig.clj file to your database credentials.

## Usage

I've been using XAMPP to run a MySQL Server on my localhost.

After that, you can use Command Prompt to run the application. Be sure that you start Command Prompt in the application folder and then run the command: **lein ring server**. This will open the application in the browser on the following URL: http://localhost:3000/.

## License

Copyright © 2022 FIXME

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
#� �c�o�m�p�e�t�i�t�i�o�n�
�
�
