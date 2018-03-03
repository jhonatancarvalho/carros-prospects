import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgHttpLoaderModule } from 'ng-http-loader/ng-http-loader.module';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app.routes';
import { CarrosComponent } from '../pages/carros/carros.component';
import { PessoaPipedriveService } from '../services/domain/pessoa-pipedrive.service';
import { CarroService } from '../services/domain/carro.service';
import { MessageComponent } from '../layout/message/message.component';

@NgModule({
  declarations: [
    AppComponent,
    MessageComponent,
    CarrosComponent
  ],
  imports: [
    AppRoutingModule,
    BrowserModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    NgHttpLoaderModule
  ],
  providers: [
    PessoaPipedriveService,
    CarroService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
