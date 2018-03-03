import { Component, OnInit } from '@angular/core';
import { PessoaPipedriveService } from '../../services/domain/pessoa-pipedrive.service';
import { PessoaPipedriveDTO } from '../../models/pessoa-pipedrive.dto';

@Component({
  selector: 'carros-component',
  templateUrl: './carros.component.html',
  styleUrls: ['./carros.component.css']
})
export class CarrosComponent implements OnInit {

  token: string;
  listaPessoasPipedrive: PessoaPipedriveDTO[];

  constructor(private pessoaPipedriveService: PessoaPipedriveService) { }

  ngOnInit() {
    
  }

  buscaPessoasPipedrive() {
    this.pessoaPipedriveService.findAll(this.token).subscribe(response => {
      this.listaPessoasPipedrive = response;
    }, error => {
      console.log(error);
    })
  }

}