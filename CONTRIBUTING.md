## **Instructions↗️**
To run the application you download the version present on [Playstore](https://bit.ly/3ZXANcg) or clone this project into your machine. Once you clone you'll need to connect the project to firebase and add the `google-services.json` file to this path:

```gradle
app\
    src\ 
       dev
```
`NOTE`
 To simulate the real feel of the application you'll have to create two or three accounts and select different genders and interests in the account creation process. To make this entire process easier you can use this [`google-services.json`](http://bit.ly/3ZCbtI4) that already has test users that i am using for development phase. 


I am using product flavours and have three flavours `dev` , `staging` and `production`. Once you have added the `google-services.json` file to the dev flavour variant path,
   * You have to check and confirm that you have selected the `devDebug` Build variant. To do this:
     * navigate to bottom left side of your android studio and select `Build Variants`
     * Under the :app level module select `devDebug`
     * Once gradle build is done Run the application
     
## Contribution
To contribute to the project clone it and make your changes. Once done with making changes, on the terminal of your android studio run
   1. * ` ./gradlew spotlessApply `
   2.  * `./gradlew ktlintFormat `
   3. * `./gradlew ktlintcheck `
 
  Once the code passes all ktlint checks push the code and open a `Pull Request`
     
## Contributor(s) 🪶🤝

Take a look at the contributors for this project. 😎
