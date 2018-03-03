import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app.routes';
import { CarrosComponent } from '../pages/carros/carros.component';
import { PessoaPipedriveService } from '../services/domain/pessoa-pipedrive.service';

@NgModule({
  declarations: [
    AppComponent,
    CarrosComponent
  ],
  imports: [
    AppRoutingModule,
    BrowserModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [
    PessoaPipedriveService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
