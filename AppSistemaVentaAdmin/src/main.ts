import { bootstrapApplication } from '@angular/platform-browser';
import { provideAnimations } from '@angular/platform-browser/animations';
import { provideRouter } from '@angular/router';
import { provideHttpClient, withInterceptorsFromDi } from '@angular/common/http';
import { importProvidersFrom } from '@angular/core';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations'; // 👈
import { routes } from './app/app.routes';
import { AppComponent } from './app/app.component';
import { SharedModule } from './app/Reutilizable/shared/shared.module';

bootstrapApplication(AppComponent, {
  providers: [
    provideRouter(routes),
    provideHttpClient(withInterceptorsFromDi()),
    provideAnimations(), // Ya lo tienes, igual déjalo
    importProvidersFrom(BrowserAnimationsModule), // 👈 Esta línea es la clave
    importProvidersFrom(SharedModule)
  ]
}).catch(err => console.error(err));
