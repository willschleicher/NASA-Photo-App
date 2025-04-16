import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {MarsRoverComponent} from "./mars-rover/mars-rover-component/mars-rover.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, MarsRoverComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'NASA Mars Rover Images';
}
