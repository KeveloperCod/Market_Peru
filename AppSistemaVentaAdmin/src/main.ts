import { bootstrapApplication } from '@angular/platform-browser';
import { BrowserModule } from '@angular/platform-browser';
import { provideRouter } from '@angular/router';
import { provideHttpClient, withInterceptorsFromDi } from '@angular/common/http';
import { provideAnimations } from '@angular/platform-browser/animations';
import { importProvidersFrom } from '@angular/core';

import { routes } from './app/app.routes';
import { AppComponent } from './app/app.component';
import { SharedModule } from './app/Reutilizable/shared/shared.module';


bootstrapApplication(AppComponent, {
  providers: [
    provideRouter(routes),              
    provideHttpClient(withInterceptorsFromDi()),
    provideAnimations(),

    importProvidersFrom(BrowserModule, SharedModule)
  ]
}).catch(err => console.error(err));
