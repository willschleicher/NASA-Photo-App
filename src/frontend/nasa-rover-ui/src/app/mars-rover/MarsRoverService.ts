import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MarsRoverService {
  private apiUrl = 'http://localhost:8080/api/marsrover';

  constructor(private http: HttpClient) { }

  processDatesFile(): Observable<string[]> {
    return this.http.post<string[]>(`${this.apiUrl}/process-dates`, {});
  }

  getImageForDate(date: string): Observable<string> {
    return this.http.get<string>(`${this.apiUrl}/image/${date}`);
  }
}
