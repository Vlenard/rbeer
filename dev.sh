#!/bin/bash

FRONTEND_PATH="frontend/"

usage(){
    echo "Használat: $0 [--frontend] [--backend] [--all]"
    echo "  --frontend      Csak a frontend indul (dev)"
    echo "  --backend       Csak a backend indul (dev)"
    exit 1
}

if [ $# -eq 0 ]; then
    usage
fi

for arg in "$@";do
    case $arg in
        --frontend)     
            cd frontend
            npm run dev
        ;;
        --backend)
            tsnd --respawn src/index.ts
        ;;
        *)
        usage
        ;;
    esac
done