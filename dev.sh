#!/bin/bash

FRONTEND_PATH="frontend/"

usage(){
    echo "Használat: $0 [--frontend] [--backend] [--all]"
    echo "  --frontend      Csak a frontend indul (dev)"
    echo "  --backend       Csak a backend indul (dev)"
    echo "  --all           Mind a kettő elindul" 
    exit 1
}

if [ $# -eq 0 ]; then
    usage
fi

for arg in "$@";do
    case $arg in
        --frontend)     
            echo "not implemented"
        ;;
        --backend)
            echo "not implemented"
        ;;
        --all)
            gnome-terminal -- bash -c "cd frontend && npm run dev; exec bash"
            gnome-terminal -- bash -c "node index.js; exec bash"   
        ;;
        *)
        usage
        ;;
    esac
done


