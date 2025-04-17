Full stack app to download and display all photos from the Mars Rover API for given dates in a dates.txt file.

-App built using Angular 17, Spring Boot 3.4.X, and Java 21.

Back end-instructions:
- run app using JDK 21
- from here you can edit the dates.txt file in the src/main/resources folder to customize the dates you'd like to download images for
- You can also locally call endpoints at http://localhost:8080/api/marsrover/process-dates to process the dates.txt file and download photos, or http://localhost:8080/api/marsrover/image/{{date}} to download photos for a specific date.
- The photos will save in the front end assets folder in src/frontend/nasa-rover-ui/src/assets/images, grouped by date

Front end: 
![image](https://github.com/user-attachments/assets/173d43b4-fef5-4741-8964-a8ab1e02e9c3)
- install angular CLI 17 using npm install -g @angular/cli @17
- In the terminal, run ng serve
- Open the running app at localhost:4200 and press the process images button to call process-dates api and parse/download the dates.txt file
- The image paths will show in the front end, and every image will display

Unit testing:
- The date parser, api client, and context are all tested in src/test/java

Futher extension:
- If I had more spare time, I would definitely make the front end more flexible with text input, or drag and drop dates to download images for, as well as perhaps putting in your own API key for higher rate limits
- I'd also unit test the front end with a front end testing framework such as jasmine
- I'd also get the app running in a docker image

