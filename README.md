#MyMovieMemoir
Assignment of FIT5046 2020 SEM 1

#### For fully using this applicaiton, there are some additonal operations need to do
* To enable the function of Map page, open [Google API Console](https://console.developers.google.com/apis) to apply a google api key, and add the key into the value of "com.google.android.geo.API_KEY" in ___AndroidManifest.xml___ and adding the api_ket into the ___com.example.mymoviememoir.network.request.base.BaseGoogleRequest___ class.
* To query the comment from Twitter in the Movie detail page, open [Twitter Developer Console](https://developer.twitter.com/en/dashboard) and apply a developer account. Put the session key and private key into ___com.example.mymoviememoir.network.request.twitter.TwitterSessionRequest___ class
* To enable the function of Movie Search page, open [The MovieDB developer Page](https://developers.themoviedb.org/) apply a developer account. Put the api_key into ___com.example.mymoviememoir.network.request.base.BaseMovieDBRequest___ class

---
![Login page](https://sunkaiiii.github.io/docs/images/5046_1.jpg)
![Sign up page](https://sunkaiiii.github.io/docs/images/5046_2.jpg)
![Home page](https://sunkaiiii.github.io/docs/images/5046_3.jpg)
![Movie search page](https://sunkaiiii.github.io/docs/images/5046_5.jpg)
![Movie detao; page](https://sunkaiiii.github.io/docs/images/5046_6.jpg)
![Add memoir page](https://sunkaiiii.github.io/docs/images/5046_7.jpg)
![Memoir page](https://sunkaiiii.github.io/docs/images/5046_8.jpg)
![Watch list page](https://sunkaiiii.github.io/docs/images/5046_9.jpg)