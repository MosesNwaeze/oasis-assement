import {Component, Inject, OnInit} from '@angular/core';
import {CommonModule, DOCUMENT} from "@angular/common";

@Component({
  selector: 'app-footer',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './footer.component.html',
  styleUrl: './footer.component.css'
})
export class FooterComponent implements OnInit{


  show = true;
  constructor(@Inject(DOCUMENT) private document:Document) {
  }

  ngOnInit(): void {

    const url = new URL(this.document.URL);
    const pathname = url.pathname;

    this.show = !(pathname === '/auth/login' || pathname === '/auth/register')

  }

}
