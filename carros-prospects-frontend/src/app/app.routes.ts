import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AppComponent } from './app.component';
import { CarrosComponent } from './pages/carros/carros.component';

const appRoutes: Routes = [
    {
        path: '',
        component: CarrosComponent
    },
	{
        path: 'carros',
        component: CarrosComponent
    },
	{ path: '**', redirectTo: ''}
]

@NgModule({
    imports: [
        RouterModule.forRoot(appRoutes)
    ],
    exports: [
        RouterModule
    ]
})
export class AppRoutingModule { }