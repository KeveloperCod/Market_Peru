import { bootstrapApplication } from '@angular/platform-browser';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { provideRouter } from '@angular/router';
import { provideHttpClient, withInterceptorsFromDi } from '@angular/common/http';
import { provideAnimations } from '@angular/platform-browser/animations';
import { importProvidersFrom } from '@angular/core';
import { HTTP_INTERCEPTORS } from '@angular/common/http';         


import { routes } from './app/app.routes';
import { AppComponent } from './app/app.component';
import { SharedModule } from './app/Reutilizable/shared/shared.module';
import { AuthInterceptor } from './app/Interceptors/auth.interceptor';


bootstrapApplication(AppComponent, {
  providers: [
    provideRouter(routes),              
    
    provideAnimations(),
 {                                             
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    },

    provideHttpClient(withInterceptorsFromDi()),
    importProvidersFrom(BrowserModule,  BrowserAnimationsModule,SharedModule)
  ]
}).catch(err => console.error(err));
