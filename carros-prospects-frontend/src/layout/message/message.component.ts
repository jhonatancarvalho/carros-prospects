import { Component, OnChanges, Input, SimpleChanges, SimpleChange } from '@angular/core';
import { MENSAGEM } from './message.constants';

@Component({
    selector: 'message',
    templateUrl: 'message.component.html'
})
export class MessageComponent implements OnChanges {

    @Input() mensagemInput: string;

    mensagem: {tipo: string, titulo?: string, texto: string, timeout?: number, listaMensagens?: Array<any>};
    classeMensagem: {};

    private currentInterval: any;

    constructor() { }

    ngOnChanges(changes: SimpleChanges): void {
        let mensagemInput: SimpleChange = changes['mensagemInput'];
        if (mensagemInput.currentValue){
            this.mostrarMensagem(mensagemInput.currentValue);
        }
    }

    fechaMensagem(): void {
        this.mensagem = undefined;
        clearInterval(this.currentInterval);
    }

    mostrarMensagem(mensagem): void {
        let tipo = mensagem.tipo ? mensagem.tipo : MENSAGEM.WARNING;
        if (tipo == MENSAGEM.ERROR) {
            tipo = MENSAGEM.DANGER;
        }
        
        let timeout: number = mensagem.timeout ? mensagem.timeout : 3000;
        let titulo = mensagem.titulo ? mensagem.titulo : this.getMensagemTitulo(tipo) + ': ';
        let texto = mensagem.texto ? mensagem.texto : 'Ocorreu algum problema ao executar o comando';
        let listaMensagens = mensagem.listaMensagens ? mensagem.listaMensagens : [];
        this.mensagem = {
            tipo: tipo,
            titulo: titulo,
            texto: texto,
            timeout: timeout,
            listaMensagens: listaMensagens
        };
        this.montarClasses(tipo);

        if (this.currentInterval){
            clearInterval(this.currentInterval);
        }

        if (mensagem.tipo === MENSAGEM.DANGER || mensagem.tipo === MENSAGEM.ERROR){
            return;
        }

        let count = timeout / 10;
        this.currentInterval = setInterval(() => {
            if (count <= 0){
                this.fechaMensagem();
            }
            count--;
        }, 10);
    }

    private getMensagemTitulo(tipo: string): string {
        switch (tipo) {
            case MENSAGEM.SUCCESS:
                return 'Sucesso';
            case MENSAGEM.INFO:
                return 'Informação';
            case MENSAGEM.DANGER:
                return 'Erro';
            case MENSAGEM.WARNING:
                return 'Aviso';
            default:
                return 'Mensagem';
        }
    }

    private montarClasses(tipo: string): void {
        this.classeMensagem = {
            'alert': true,
            'alert-dismissable': true,
            'animated': true,
            'fadeInDown': true
        };
        this.classeMensagem['alert-' + tipo] = true;
    }

}