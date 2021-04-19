//import { Fragment, useMemo } from 'react';
import Helmet from 'react-helmet';
//import { useActivities } from 'hooks/Activities';

// Material UI Components
import { makeStyles } from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';

// Project Components
import Navigation from 'components/navigation/Navigation';
//import Pagination from 'components/layout/Pagination';
//import Paper from 'components/layout/Paper';
//import NotFoundIndicator from 'components/miscellaneous/NotFoundIndicator';
// import ActivityCard from 'components/layout/ActivityCard';

import MasonryGrid from 'components/layout/MasonryGrid';
import SearchBar from 'components/inputs/SearchBar';

const useStyles = makeStyles((theme) => ({
  root: {
    paddingBottom: theme.spacing(2),
  },
  list: {
    display: 'grid',
    gridTemplateColumns: '1fr 1fr 1fr',
    gap: theme.spacing(0, 1),
    [theme.breakpoints.down('lg')]: {
      gap: theme.spacing(1),
      gridTemplateColumns: '1fr',
    },
  },
  first: {
    gridColumn: 'span 3',
    [theme.breakpoints.down('lg')]: {
      gridColumn: 'span 1',
    },
  },
  suggestedActivities: {
    paddingTop: theme.spacing(6),
  },
  newActivities: {},
  title: {
    height: 150,
    textAlign: 'left',
  },
  wrapper: {
    marginTop: '100px',
    textAlign: 'center',
  },
  searchWrapper: {
    display: 'flex',
    maxWidth: '70%',
    maxHeight: '60px',
    margin: 'auto',
    gap: theme.spacing(1),
  },
}));

const Activities = () => {
  const classes = useStyles();
  //const { data, error, hasNextPage, fetchNextPage, isFetching } = useActivities();
  //const isEmpty = useMemo(() => (data !== undefined ? !data.pages.some((page) => Boolean(page.results.length)) : false), [data]);

  return (
    <Navigation>
      <Helmet>
        <title>Aktiviteter</title>
      </Helmet>
      <div className={classes.wrapper}>
        <div className={classes.searchWrapper}>
          <SearchBar />
        </div>
        <div className={classes.suggestedActivities}>
          <MasonryGrid>
            <div className={classes.title}>
              <Typography variant='h1'>Aktiviteter nær deg</Typography>
            </div>
            {/*<ActivityCard /> */}
          </MasonryGrid>
        </div>
        <div className={classes.newActivities}>
          <MasonryGrid>
            <div className={classes.title}>
              <Typography variant='h1'>Nye Aktiviteter</Typography>
            </div>
            {/*<ActivityCard /> */}
          </MasonryGrid>
        </div>
      </div>
      {/*<div className={classes.root}>
         {isLoading && <ListItemLoading />} 
        {isEmpty && <NotFoundIndicator header='Fant ingen aktiviteter' />}
        {error && <Paper>{error.detail}</Paper>}
        {data !== undefined && (
          <Pagination fullWidth hasNextPage={hasNextPage} isLoading={isFetching} nextPage={() => fetchNextPage()}>
            <div className={classes.list}>
              {data.pages.map((page, i) => (
                <Fragment key={i}>
                  {page.results.map((newsItem, j) => (
                    <Typography key={j} variant='h2'>
                      - {newsItem.title}
                    </Typography>
                  ))}
                </Fragment>
              ))}
            </div>
          </Pagination>
        )}
        { {isFetching && <ListItemLoading />} }
      </div>*/}
    </Navigation>
  );
};

export default Activities;
