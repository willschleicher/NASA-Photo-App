import {Component, OnInit} from '@angular/core';
import {MarsRoverService} from "../MarsRoverService";
import {NgForOf, NgIf} from "@angular/common";

@Component({
  selector: 'app-mars-rover-component',
  standalone: true,
  imports: [
    NgIf,
    NgForOf
  ],
  templateUrl: './mars-rover.component.html',
  styleUrl: './mars-rover.component.css'
})
export class MarsRoverComponent implements OnInit {
  images: string[] = [];
  loading = false;
  error: string | null = null;

  constructor(private marsRoverService: MarsRoverService) { }

  ngOnInit(): void {}

  processDates(): void {
    this.loading = true;
    this.error = null;

    this.marsRoverService.processDatesFile().subscribe({
      next: (images) => {
        this.images = images;
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Error processing dates: ' + err.message;
        this.loading = false;
      }
    });
  }
}
