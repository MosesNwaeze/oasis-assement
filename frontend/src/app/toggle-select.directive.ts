import {Directive, ElementRef, HostListener, Renderer2} from '@angular/core';

@Directive({
  selector: '[appToggleSelect]',
  standalone: true
})

export class ToggleSelectDirective {
  constructor(private el: ElementRef, private renderer: Renderer2) {}

  @HostListener('click') onClick() {
    const selectElement = this.el.nativeElement.nextElementSibling;
    if (selectElement) {
      this.renderer.setProperty(selectElement, 'style.display', 'block');
    }
  }
}

