**NOTE: This was a group assignment. View all commits carefully to get an insight into my abilities. The end result is suboptimal due to complications during this project. Please contact me for more information.**

<p align="center"><img src="./app/src/main/ic_launcher-web.png?raw=true" width="200px"/></p>

<h1 align="center">Kolveniershof Android application</h1>

The 'Kolveniershof' Android application is an application made to guide mentally disabled people through their weekly planning at the day care institution ([Ave Regina](https://www.averegina.be/vz---dagondersteuning.html)).

Users can log in anywhere to view their schedule and possibly provide comments.
In the day care institution itself, supervisors can request the planning of each client and go over it with the client.

> It is not intended that this application is used to adjust the schedule.

This project is part of the [Project III: Mobile apps](https://bamaflexweb.hogent.be/BMFUIDetailxOLOD.aspx?a=110488&b=1&c=1) course for the Bachelor of Applied Informatics at the Ghent University College [HoGent](https://www.hogent.be/en/) (Academic year 2019-2020).

<!-- TODO - Add screenshots
## Screenshots

<p align="center">
    <img src="./screenshots/.jpg?raw=true" width="256px">
    <img src="./screenshots/.jpg?raw=true" width="256px">
    <img src="./screenshots/.jpg?raw=true" width="256px">
    <img src="./screenshots/.jpg?raw=true" width="256px">
    <img src="./screenshots/.jpg?raw=true" width="256px">
    <img src="./screenshots/.jpg?raw=true" width="256px">
    <img src="./screenshots/.jpg?raw=true" width="256px">
    <img src="./screenshots/speed_camera_new.jpg?raw=true" width="256px">
    <img src="./screenshots/police_check_new.jpg?raw=true" width="256px">
</p>
-->

---

## Getting Started

<!-- TODO - Change href
You can download the application on the Google Play store.

<a href=''><img alt='Get it on Google Play' src='https://play.google.com/intl/en_us/badges/static/images/badges/en_badge_web_generic.png' height='100px'/></a>
-->

### Installation

1. Clone this repo

    ```bash
    git clone https://github.com/VictorOwnt/KolveniershofAndroid
    ```

2. Open the project in Android Studio

    ```bash
    studio KolveniershofAndroid
    ```

3. Run the project on an emulator or physical device

#### Dummy login

Use the login credentials stated below to test the project's functionality.

Client:

- Email: *`client@mail.com`*
- Password: *`test00##`*

Supervisor:

- Email: *`mentor@mail.com`*
- Password: *`test00##`*

### Generating signed APK

From Android Studio:

1. ***Build*** menu
2. ***Generate Signed APK...***
3. Fill in the keystore information *(you only need to do this once manually and then let Android Studio remember it)* OPTIONAL

### REST backend

This application relies on a REST backend server.

1. Open the `Constants.kt` file located in the `util` package.
2. Change the value for `BASE_URL` to your own link.
3. Create a new Firebase project and change the `google-services.json` file accordingly.

The sourcecode for this server is located [here](https://github.com/VictorOwnt/KolveniershofBackend). The server is hosted by [Heroku](https://www.heroku.com/) [![Heroku Backend Status](http://heroku-shields.herokuapp.com/kolv02-backend)](https://kolv02-backend.herokuapp.com).

## Built With

* [Retrofit](https://square.github.io/retrofit/)
* [Dagger](https://github.com/google/dagger)
* [ReactiveX](http://reactivex.io/)
* [FireBase](https://firebase.google.com/)
* [Glide](https://bumptech.github.io/glide/)
* [Joda-Time](https://www.joda.org/joda-time/)

## Team

| <a href="https://github.com/JakobLierman" target="_blank">**Jakob Lierman**</a> | <a href="https://github.com/RubenDeFreyne" target="_blank">**Ruben De Freyne**</a>  | <a href="https://github.com/VictorOwnt" target="_blank">**Victor Van Hulle**</a> | <a href="https://github.com/reeveng" target="_blank">**Reeven Govaert**</a> | <a href="https://github.com/SWeB06" target="_blank">**Sebastien Wojtyla**</a> |<a href="https://github.com/WoutMaes" target="_blank">**Wout Maes**</a> |
| --- | --- | --- | --- | --- | --- |
| [![Jakob](https://avatars2.githubusercontent.com/u/25779630?s=200)](https://github.com/JakobLierman) | [![Ruben](https://avatars2.githubusercontent.com/u/25815999?s=200)](https://github.com/RubenDeFreyne) | [![Victor](https://avatars2.githubusercontent.com/u/17174095?s=200)](https://github.com/VictorOwnt) | [![Reeven](https://avatars3.githubusercontent.com/u/36441093?s=200)](https://github.com/reeveng)| [![Sebastien](https://avatars2.githubusercontent.com/u/36441058?s=200)](https://github.com/SWeB06) | [![Wout](https://avatars0.githubusercontent.com/u/36442271?s=200)](https://github.com/WoutMaes)
