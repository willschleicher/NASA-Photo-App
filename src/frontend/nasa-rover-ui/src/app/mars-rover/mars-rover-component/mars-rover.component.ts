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
  styleUrls: ['./mars-rover.component.css']
})
export class MarsRoverComponent implements OnInit {
  rawPaths: string[] = [];
  assetUrls: string[] = [];
  loading = false;
  error: string | null = null;

  constructor(private marsRoverService: MarsRoverService) {
  }

  ngOnInit(): void {
  }

  processDates(): void {
    this.loading = true;
    this.error = null;
    this.marsRoverService.processDatesFile().subscribe({
      next: paths => {
        this.rawPaths = paths;
        this.assetUrls = paths.map(p => this.toAssetUrl(p));
        this.loading = false;
      },
      error: err => {
        this.error = 'Error processing dates: ' + err.message;
        this.loading = false;
      }
    });
  }

  private toAssetUrl(fullPath: string): string {
    // 1) normalize backslashes
    const unixPath = fullPath.replace(/\\/g, '/');
    // 2) find "assets/" and grab everything after it
    const idx = unixPath.indexOf('assets/');
    if (idx >= 0) {
      return '/' + unixPath.substring(idx);
    }
    // fallback: assume it already is relative to assets
    return '/assets/' + unixPath;
  }
}
